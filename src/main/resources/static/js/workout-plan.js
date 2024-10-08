const tableContainer = document.querySelector('.table-container');
const table = document.createElement('table');
const thead = document.createElement('thead');
const tbody = document.createElement('tbody');
const form = document.getElementById('dynamic-form');
const addInputBtn = document.getElementById('add-btn');
const submitBtn = document.getElementById('submit-btn');
let counter = 1;
let userIdElement = document.getElementById('user-id');
let userId = userIdElement.textContent;
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
fetch('/user/getAuthUser')
    .then(response => response.json())
    .then(data => {
        userId = data.id;
    });
addInputBtn.addEventListener('click', function(event) {
    event.preventDefault();
    counter++;

    const newFormGroup = document.createElement('div');
    newFormGroup.className = 'inputBox';
    newFormGroup.classList.add('form-group');

    const newInput1 = document.createElement('input');
    newInput1.type = 'text';
    newInput1.name = "exercises" + counter;
    newInput1.id = "exercises" + counter;
    newInput1.required = true;
    newFormGroup.appendChild(newInput1);

    const newI1 = document.createElement('i');
    newI1.textContent = 'Упражнение ' + counter;
    newFormGroup.appendChild(newI1);

    const newInput2 = document.createElement('input');
    newInput2.type = 'text';
    newInput2.name = "repetitions" + counter;
    newInput2.id = "repetitions" + counter;
    newInput2.required = true;
    newFormGroup.appendChild(newInput2);

    const newI2 = document.createElement('i');
    newI2.textContent = 'Повторения';
    newFormGroup.appendChild(newI2);

    form.insertBefore(newFormGroup, addInputBtn);

});
submitBtn.addEventListener('click', function(event) {
    event.preventDefault();

    const formData = {};
    const inputs = document.querySelectorAll('.form input');

    const exercisesList = [];
    const repetitionsList = [];

    inputs.forEach(function(input) {
        // Проверяем имя input и добавляем данные в соответствующий список
        if (input.name.includes('exercises')) {
            exercisesList.push(input.value);
        } else if (input.name.includes('repetitions')) {
            repetitionsList.push(input.value);
        } else if (input.type !== 'submit') {
            formData[input.name] = input.value;
        }
    });
    formData['exercises'] = exercisesList;
    formData['repetitions'] = repetitionsList;
    formData['userId'] = userId;

    fetch('/submitForm', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            console.log(response)
            window.location.href = '/api/v1/getWorkoutPage/' + userId
        })
        .catch(error => {
            console.error('Ошибка при отправке формы:', error);
        });

});

// fetch('/user/getWorkoutPlan')