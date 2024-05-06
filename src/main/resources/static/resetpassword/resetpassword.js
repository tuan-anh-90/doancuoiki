function resetPassword() {
    const inputPassword = document.getElementById('inputPassword').value;
    const inputPasswordAgain = document.getElementById('inputPasswordAgain').value;

    // Kiểm tra xem hai mật khẩu có trùng nhau không
    if (inputPassword !== inputPasswordAgain) {
        alert('Mật khẩu không trùng khớp. Vui lòng nhập lại.');
        return;
    }

    // Lấy token từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');

    if (!token) {
        alert('Không tìm thấy token.');
        return;
    }
    console.log(token);
    // Gọi API để cập nhật mật khẩu
    const url = 'http://localhost:8081/api/reset-password/updatePassword'; // Thay đổi thành URL của API thực tế
    const data = {
        getToken: token,
        newPassword: inputPassword
    };

    // Gọi API cập nhật mật khẩu
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Cập nhật mật khẩu thất bại.');
        }
        alert('Mật khẩu đã được cập nhật thành công.');
        window.location.href = '/signin';
    })
    .catch(error => {
        console.error('Lỗi:', error);
        alert('Có lỗi xảy ra. Vui lòng thử lại sau.');
    });
}
