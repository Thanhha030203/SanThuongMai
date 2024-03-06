var hrBar1 = document.getElementById('spanStatus-orderDetail')
if (hrBar1.textContent == "Chưa thanh toán" || hrBar1.textContent == "Đã thanh toán" || hrBar1.textContent == "Chờ xác nhận") {
    document.querySelector('.bar1').style.backgroundColor = "#c0c0c0"
} else {
    document.querySelector('.bar1').style.backgroundColor = "#F25019";
}
var hrBar2 = document.getElementById('spanStatus-orderDetail')
if (hrBar2.textContent == "Chưa thanh toán" || hrBar2.textContent == "Đã thanh toán" || hrBar2.textContent == "Chờ xác nhận" || hrBar2.textContent == "Đang vận chuyển") {
    document.querySelector('.bar2').style.backgroundColor = "#c0c0c0"
} else {
    document.querySelector('.bar2').style.backgroundColor = "#F25019";
}