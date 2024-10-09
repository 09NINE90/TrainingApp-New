const addBtn = document.querySelector('#add-physical-parameters-btn')
const token = $("meta[name='_csrf']").attr("content");
addBtn.addEventListener('click', ()=>{
    const weight = document.querySelector('#weight')
    const legGirth = document.querySelector('#leg-girth')
    const armCircumference = document.querySelector('#arm-circumference')
    const hipCircumference = document.querySelector('#hip-circumference')
    const waistCircumference = document.querySelector('#waist-circumference')
    const chestCircumference = document.querySelector('#chest-circumference')
    const date = document.querySelector('#date')

    if (
        weight.value &&
        legGirth.value &&
        armCircumference.value &&
        hipCircumference.value &&
        waistCircumference.value &&
        chestCircumference.value &&
        date.value
    ){
        const requestData = {
            weight: weight.value,
            legGirth: legGirth.value,
            armCircumference: armCircumference.value,
            hipCircumference: hipCircumference.value,
            waistCircumference: waistCircumference.value,
            chestCircumference: chestCircumference.value,
            date: date.value
        }
        console.log(requestData)
        fetch('/user/addPhysicalParameters', {
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
                console.error('Ошибка при добавлении параметров:', error);
            });
    }
    else {
        alert("Заполните все поля")
    }
})