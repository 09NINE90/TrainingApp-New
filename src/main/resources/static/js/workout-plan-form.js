const form = document.getElementById('dynamic-form');
const addExerciseBtn = document.getElementById('add-exercise-btn');
const createWorkoutPlanBtn = document.getElementById('create-workout-plan-btn');

const userId = $("meta[name='user-id']").attr("content");
const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");

let counter = 1;
addExerciseBtn.addEventListener('click', function(event) {
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

    form.insertBefore(newFormGroup, addExerciseBtn);

});
createWorkoutPlanBtn.addEventListener('click', function(event) {
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
        } else if (input.type !== 'button') {
            formData[input.name] = input.value;
        }
    });
    formData['exercises'] = exercisesList;
    formData['repetitions'] = repetitionsList;
    formData['userId'] = userId;

    fetch('/user/createWorkoutPlan', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok){
                console.log(response)
                window.location.href = `/user/getWorkoutPlanPageByUserId/${userId}`;
            }
        })
        .catch(error => {
            console.error('Ошибка при отправке формы:', error);
        });

});

// fetch('/user/getWorkoutPlan')