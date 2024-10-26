use jni::{objects::JString, JNIEnv};

use crate::Result;

pub trait JStringToString {
    fn to_string(&self, env: &mut JNIEnv) -> Result<String>;
}

impl<'r> JStringToString for JString<'r> {
    fn to_string(&self, env: &mut JNIEnv) -> Result<String> {
        Ok(env.get_string(&self)?.to_str()?.to_owned())
    }
}
