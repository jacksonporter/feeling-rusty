use uniffi::export;

#[derive(Debug, thiserror::Error, uniffi::Error)]
pub enum MathError {
    #[error("division by zero")]
    DivisionByZero,
}

#[export]
pub fn add(a: i64, b: i64) -> i64 {
    a + b
}

#[export]
pub fn divide(a: f64, b: f64) -> Result<f64, MathError> {
    if b == 0.0 {
        Err(MathError::DivisionByZero)
    } else {
        Ok(a / b)
    }
}

uniffi::setup_scaffolding!();
