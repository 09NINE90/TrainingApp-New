const signUpBtn = document.querySelector('#signup-btn');
const passwordInputS= document.querySelectorAll('#password');
const confirmPasswordInputS= document.querySelectorAll('#confirm-password');
const token = $("meta[name='_csrf']").attr("content");
for (const elem of confirmPasswordInputS) {
    elem.addEventListener("input", checkPassword);
    elem.addEventListener("focus", checkPassword);
    elem.addEventListener("blur", checkPassword);
}

for (const elem of passwordInputS) {
    elem.addEventListener("input", checkPassword);
    elem.addEventListener("focus", checkPassword);
    elem.addEventListener("blur", checkPassword);
}

function checkPassword(){
    const passwordInput= document.querySelector('#password');
    const confirmPasswordInput= document.querySelector('#confirm-password');
    if (passwordInput.value !== ''){
        if (passwordInput.value !== confirmPasswordInput.value){
            signUpBtn.disabled = true;
            passwordInput.style.background = '#9a1400';
            confirmPasswordInput.style.background = '#9a1400';
        }else {
            signUpBtn.disabled = false;
            passwordInput.style.background = '#138f00';
            confirmPasswordInput.style.background = '#138f00';
        }
    }else {
        signUpBtn.disabled = true;
        passwordInput.style.background = '#333';
        confirmPasswordInput.style.background = '#333';
    }
}

signUpBtn.addEventListener('click', ()=>{
    const firstName = document.querySelector('#first-name')
    const lastName = document.querySelector('#last-name')
    const email = document.querySelector('#email')
    const phoneNumber = document.querySelector('#phone-number')
    const password = document.querySelector('#password')
    if (
        firstName.value &&
        lastName.value &&
        email.value &&
        phoneNumber.value &&
        password.value
    ){
        const requestData = {
            firstName: firstName.value,
            lastName: lastName.value,
            username: email.value,
            phoneNumber: phoneNumber.value,
            password: password.value
        };
        fetch('/user/createUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token
            },
            body: JSON.stringify(requestData),
        })
            .then((response) => {
                console.log(response);
                if (response.ok){
                    window.location.href = '/user/mainPage';
                }
            })
            .catch((error) => {
                console.error('Ошибка при регистрации:', error);
            });
    }
})