{
    let timer;
    let remainingTime;

// Hàm để tính toán và hiển thị bộ đếm ngược
    function startCountdown() {
        // Lấy thời gian kết thúc từ server
        const endTimeValue = document.getElementById("endTime").value;

        // Kiểm tra nếu endTime là hợp lệ
        const endTime = new Date(endTimeValue).getTime(); // Chuyển đổi giá trị endTime thành đối tượng Date

        if (isNaN(endTime)) {
            console.error("End time không hợp lệ:", endTimeValue);
            return;
        }

        // Nếu có thời gian còn lại trong sessionStorage, sử dụng nó
        if (sessionStorage.getItem("remainingTime")) {
            remainingTime = parseInt(sessionStorage.getItem("remainingTime"));
        } else {
            remainingTime = endTime - new Date().getTime();
        }

        // Cập nhật bộ đếm ngược mỗi giây
        const countdownElement = document.getElementById("countdown");

        function updateCountdown() {
            if (remainingTime <= 0) {
                countdownElement.innerHTML = "Thời gian đã hết!";
                document.getElementById("form").submit();  // Nộp form
                clearInterval(timer);  // Dừng bộ đếm ngược
            } else {
                const secondsLeft = Math.floor(remainingTime / 1000);
                const minutes = Math.floor(secondsLeft / 60);
                const seconds = secondsLeft % 60;

                countdownElement.innerHTML = minutes + " phút " + seconds + " giây";
                remainingTime -= 1000;  // Giảm thời gian còn lại mỗi giây
                sessionStorage.setItem("remainingTime", remainingTime);  // Lưu thời gian còn lại vào sessionStorage
            }
        }

        // Cập nhật mỗi giây
        timer = setInterval(updateCountdown, 1000);
    }

// Hàm để ngừng bộ đếm ngược khi người dùng nộp bài
    function stopCountdown(event) {
        const confirmSubmit = confirm("Bạn có chắc chắn muốn nộp bài không?");

        if (confirmSubmit) {
            // Nếu người dùng xác nhận, gửi form nộp bài
            document.getElementById("form").submit();
            clearInterval(timer);  // Ngừng bộ đếm ngược
            sessionStorage.removeItem("remainingTime");  // Xóa dữ liệu khỏi sessionStorage khi nộp bài
        } else {
            // Nếu người dùng hủy bỏ, không làm gì cả
            event.preventDefault();
        }

    }
    window.onload = startCountdown;
}