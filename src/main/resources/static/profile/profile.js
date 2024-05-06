// Hàm để lấy thông tin người dùng từ API và hiển thị trên trang web
function fetchAndDisplayUserInfo() {
  fetch("/api/users/current-user") // Đổi đường dẫn API tương ứng với endpoint của bạn
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then((data) => {
      console.log(data);
      // Hiển thị thông tin người dùng
      document.getElementById(
        "avatar"
      ).style.backgroundImage = `url('https://www.pngall.com/wp-content/uploads/5/User-Profile-PNG-HD-Image.png')`;
      document.getElementById("name").innerText = data.name;
      document.getElementById("maNV").innerText = data.maNV;
      document.getElementById("email").value = data.email;
      document.getElementById("gioiTinh").value = data.gioiTinh;
      document.getElementById("chucVu").value = data.chucVu;
      document.getElementById("loaiTaiKhoan").value = data.role;

      // Thêm các dòng tương ứng với các trường thông tin khác
    })
    .catch((error) => {
      console.error("Error fetching user information:", error);
      alert("Error fetching user information");
    });
}

// Gọi hàm để lấy và hiển thị thông tin người dùng khi trang được tải
document.addEventListener("DOMContentLoaded", function () {
  fetchAndDisplayUserInfo();
});
