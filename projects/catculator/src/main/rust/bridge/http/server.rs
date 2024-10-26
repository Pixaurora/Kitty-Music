use crate::{
    bridge::{drop_box, pack_box, pull_box},
    Result,
};

use jni::{
    objects::{JClass, JObject, JString},
    sys::jlong,
    JNIEnv,
};
use tokio::runtime::Runtime;

use crate::http::server::Server;

fn run_server<'r>(object: &JObject<'r>, env: &mut JNIEnv<'r>) -> Result<JString<'r>> {
    let server = pull_box::<Server>(env, object)?;

    let runtime = Runtime::new()?;
    let token = runtime.block_on(server.run_server(&runtime))?;

    Ok(env.new_string(token)?)
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_ServerImpl_create<'r>(
    mut _env: JNIEnv<'r>,
    _class: JClass<'r>,
) -> jlong {
    pack_box(Server::new())
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_ServerImpl_run0<'r>(
    mut env: JNIEnv<'r>,
    object: JObject<'r>,
) -> JString<'r> {
    match run_server(&object, &mut env) {
        Ok(token) => return token,
        Err(error) => error.throw(&mut env),
    }

    JString::default()
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_impl_http_ServerImpl_drop<'r>(
    mut env: JNIEnv<'r>,
    object: JObject<'r>,
) -> () {
    if let Err(error) = drop_box::<Server>(&mut env, &object) {
        panic!("Couldn't drop http server due to an error! {}", error);
    }
}
