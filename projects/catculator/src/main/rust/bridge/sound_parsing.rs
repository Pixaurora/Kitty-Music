use jni::{
    objects::{JClass, JString},
    sys::jlong,
    JNIEnv,
};

use crate::sound_parsing::audio_length;
use crate::Result;

fn parse_duration<'r>(env: &mut JNIEnv<'r>, path: &JString<'r>) -> Result<jlong> {
    let path: String = env.get_string(path)?.into();

    let duration = audio_length(path)?;

    Ok(duration.as_nanos().try_into()?)
}

#[no_mangle]
pub extern "system" fn Java_net_pixaurora_catculator_api_music_SoundFile_parseDuration0<'r>(
    mut env: JNIEnv<'r>,
    _class: JClass<'r>,
    path: JString<'r>,
) -> jlong {
    match parse_duration(&mut env, &path) {
        Ok(duration) => return duration,
        Err(error) => error.throw(&mut env),
    }

    jlong::default()
}
