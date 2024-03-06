const inputs = document.getElementById("inputs"); 
  var otp = '';
inputs.addEventListener("input", function (e) { 
    const target = e.target; 
    const val = target.value; 
  
    if (isNaN(val)) { 
        target.value = ""; 
        return; 
    } 
    if (val != "") { 
        otp = otp+val;
        const next = target.nextElementSibling; 
        if (next) { 
            next.focus(); 
        } 
        else{ 
           confirmOtp(otp); 
        }
    } 
}); 

var count = 3;
function confirmOtp(otp) {
	console.log("otp : "+ otp);
	$.ajax({
		url: "http://localhost:8080/rest/otp",
		type: "POST",
		data: otp,
		success: function (resultData) {
            if(resultData =="OK"){
               location.href = "http://localhost:8080/account/newpass"
            }
            else{
                count = count-1; 
                if(count == 0){ 
                    location.href = "http://localhost:8080/account/forgotPassword";
                }
                document.getElementById("message").innerText="Mã xác thực không hợp lệ ! "
                document.getElementById("count").innerText="Số lần nhập : "+ count;
            }
		},
	});
}
  
inputs.addEventListener("keyup", function (e) { 
    const target = e.target; 
    const key = e.key.toLowerCase(); 
  
    if (key == "backspace" || key == "delete") { 
        otp = otp.length-1;
        target.value = ""; 
        if (isNaN(otp)) { 
            otp='';
        } 
        const prev = target.previousElementSibling; 
        if (prev) { 
            prev.focus(); 
        } 
        return; 
    } 
});