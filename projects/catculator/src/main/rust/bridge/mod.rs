use crate::Result;
use jni::{
    objects::{JObject, JValue},
    sys::jlong,
    JNIEnv,
};

mod http {
    mod client;
    mod request_builder;
    mod server;
}
mod sound_parsing;

const UTIL_CLASS: &str = "net/pixaurora/catculator/impl/util/JniUtil";

fn pack_box<T>(t: T) -> jlong {
    Box::into_raw(Box::from(t)) as jlong
}

fn pack_box_into<T>(env: &mut JNIEnv, object: &JObject, t: T) -> Result<()> {
    let pointer = pack_box(t);
    env.set_field(object, "ptr", "J", JValue::Long(pointer))?;

    Ok(())
}

/**
Receive ownership of the boxed item owned by the Java object.
*/
fn haul_box<T>(env: &mut JNIEnv, object: &JObject) -> Result<T> {
    let pointer = get_pointer(env, object)?;

    #[allow(unused_variables)]
    let value = unsafe { Box::from_raw(pointer as *mut T) };

    Ok(*value)
}

fn drop_box<T>(env: &mut JNIEnv, object: &JObject) -> Result<()> {
    let pointer = get_pointer(env, object)?;

    #[allow(unused_variables)]
    let value = unsafe { Box::from_raw(pointer as *mut T) };

    Ok(())
}

fn pull_box<'r, T>(env: &mut JNIEnv, object: &JObject) -> Result<&'r mut T> {
    let pointer = get_pointer(env, object)?;
    Ok(unsafe { &mut *(pointer as *mut T) })
}

fn get_pointer(env: &mut JNIEnv, object: &JObject) -> Result<jlong> {
    Ok(env.get_field(object, "ptr", "J")?.j()?)
}

fn new_map<'r>(env: &mut JNIEnv<'r>) -> Result<JObject<'r>> {
    let class = env.find_class(UTIL_CLASS)?;
    let object = env
        .call_static_method(class, "newMap", "()Ljava/util/Map;", &[])?
        .l()?;

    Ok(object)
}
