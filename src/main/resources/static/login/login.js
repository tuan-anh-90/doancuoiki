//Reset password
async function sendResetPasswordEmail() {
  const email = document.getElementById("forgotEmail").value;

  try {
    const apiUrl = `http://localhost:8081/api/reset-password/sendResetPasswordEmail?email=${email}`;
    const response = await fetch(apiUrl, {
      method: "POST", // Since your API might be configured to accept POST
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (response.ok) {
      alert("Đã gửi");
      closeForgotPasswordPopup();
    } else {
      const errorMessage = await response.text();
      alert("Có lỗi xảy ra: " + errorMessage);
    }
  } catch (error) {
    console.error("Có lỗi xảy ra:", error);
    alert("Có lỗi xảy ra khi gửi email đặt lại mật khẩu.");
  }
}

document
  .getElementById("forgotPasswordForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Prevents the form from submitting and refreshing the page
    sendResetPasswordEmail();
  });
