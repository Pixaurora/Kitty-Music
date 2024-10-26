mod bridge;
pub mod errors;

pub mod http {
    pub mod server;
}
pub mod sound_parsing;
pub mod utils;

pub use errors::Error;
pub use errors::Result;

#[cfg(test)]
mod test;
