use jni::{
    objects::{JByteArray, JObject, JString, JValue},
    sys::jint,
    JNIEnv,
};
use reqwest::{blocking::RequestBuilder, header::HeaderValue};

use crate::{
    bridge::{haul_box, new_map, pack_box_into},
    utils::JStringToString,
    Result,
};

const RESPONSE_CLASS: &str = "net/pixaurora/catculator/impl/http/ResponseImpl";

fn send<'r>(env: &mut JNIEnv<'r>, this: &JObject<'r>) -> Result<JObject<'r>> {
    let builder = haul_box::<RequestBuilder>(env, this)?;

    let (client, request) = builder.build_split();
    let mut request = request?;

    // For some reason Reqwest doesn't set this by default
    if !request.method().is_safe() && !request.headers().contains_key("Content-Length") {
        request
            .headers_mut()
            .append("Content-Length", HeaderValue::from_str("0").unwrap());
    }

    let response = client.execute(request)?;

    let status: jint = response.status().as_u16().into();

    let map = new_map(env)?;
    let map_ops = env.get_map(&map)?;

    for (name, value) in response.headers().iter() {
        let name = env.new_string(name.as_str())?;
        let value = env.new_string(value.to_str()?)?;

        map_ops.put(env, &name, &value)?;
    }

    let data = response.bytes()?.to_vec();
    let body = env.new_byte_array(data.len() as i32)?;

    let buffer: Vec<i8> = unsafe { std::mem::transmute(data) };
    env.set_byte_array_region(&body, 0, &buffer[..])?;

    let class = env.find_class(RESPONSE_CLASS)?;
    let instance = env.new_object(
        class,
        "(I[BLjava/util/Map;)V",
        &[
            JValue::Int(status),
            JValue::Object(&body),
            JValue::Object(&map),
        ],
    )?;

    Ok(instance)
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_RequestBuilderImpl_send0<'r>(
    mut env: JNIEnv<'r>,
    this: JObject<'r>,
) -> JObject<'r> {
    match send(&mut env, &this) {
        Ok(response) => return response,
        Err(error) => error.throw(&mut env),
    };

    JObject::null()
}

fn body<'r>(env: &mut JNIEnv<'r>, this: &JObject<'r>, data: JByteArray<'r>) -> Result<()> {
    let request_builder = haul_box::<RequestBuilder>(env, this)?;

    let length = env.get_array_length(&data)? as usize;
    let mut buffer = vec![0; length];

    env.get_byte_array_region(data, 0, &mut buffer[..])?;
    let buffer: Vec<u8> = unsafe { std::mem::transmute(buffer) };

    pack_box_into(
        env,
        this,
        request_builder
            .body(buffer)
            .header("Content-Length", length),
    )?;

    Ok(())
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_RequestBuilderImpl_body0<'r>(
    mut env: JNIEnv<'r>,
    this: JObject<'r>,
    data: JByteArray<'r>,
) -> () {
    if let Err(error) = body(&mut env, &this, data) {
        error.throw(&mut env);
    }
}

fn query<'r>(
    env: &mut JNIEnv<'r>,
    this: &JObject<'r>,
    key: JString<'r>,
    value: JString<'r>,
) -> Result<()> {
    let request_builder = haul_box::<RequestBuilder>(env, this)?;

    let key = key.to_string(env)?;
    let value = value.to_string(env)?;

    pack_box_into(env, this, request_builder.query(&[(key, value)]))?;

    Ok(())
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_RequestBuilderImpl_query0<'r>(
    mut env: JNIEnv<'r>,
    this: JObject<'r>,
    key: JString<'r>,
    value: JString<'r>,
) -> () {
    if let Err(error) = query(&mut env, &this, key, value) {
        error.throw(&mut env);
    }
}

fn header<'r>(
    env: &mut JNIEnv<'r>,
    this: &JObject<'r>,
    key: JString<'r>,
    value: JString<'r>,
) -> Result<()> {
    let request_builder = haul_box::<RequestBuilder>(env, this)?;

    let key = key.to_string(env)?;
    let value = value.to_string(env)?;

    pack_box_into(env, this, request_builder.header(key, value))?;

    Ok(())
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_RequestBuilderImpl_header0<'r>(
    mut env: JNIEnv<'r>,
    this: JObject<'r>,
    key: JString<'r>,
    value: JString<'r>,
) -> () {
    if let Err(error) = header(&mut env, &this, key, value) {
        error.throw(&mut env);
    }
}
