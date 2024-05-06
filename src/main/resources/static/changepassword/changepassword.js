document.addEventListener("DOMContentLoaded", () => {
  const changePasswordButton = document.querySelector(".changePassword button");

  const userId = 3; //lay id ben trang login

  changePasswordButton.addEventListener("click", () => {
    const oldPassword = document.getElementById("inputOldPassword").value;
    const newPassword = document.getElementById("inputNewPassword").value;
    const newPasswordAgain = document.getElementById(
      "inputNewPasswordAgain"
    ).value;

    if (newPassword !== newPasswordAgain) {
      alert("Mật khẩu mới không khớp. Vui lòng nhập lại.");
      return;
    }

    const data = {
      id: userId,
      password: newPassword,
    };

    fetch(`http://localhost:8081/api/users/chagePassword/${userId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((updatedUserDTO) => {
        alert("Mật khẩu đã được thay đổi thành công. Đăng nhập để tiếp tục");
        window.location.href = "/login";
      })
      .catch((error) => {
        console.error("Lỗi:", error);
        alert("Đã xảy ra lỗi khi thay đổi mật khẩu.");
      });
  });
});
