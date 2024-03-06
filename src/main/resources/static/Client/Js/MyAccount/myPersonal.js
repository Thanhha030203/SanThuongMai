
function inputFullname(){
    var inputName = document.getElementById('textName');
    inputName.removeAttribute("disabled");
    inputName.classList.add('inputMypersonal');
}
function inputEmail(){
    var inputEmails = document.getElementById('textEmail');
    inputEmails.removeAttribute("disabled");
    inputEmails.classList.add('inputMypersonal');
}

function previewImage(event) {
    var input = event.target;
    var logoMypersonal = document.getElementById('logoMypersonal');

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            logoMypersonal.src = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
    }
}