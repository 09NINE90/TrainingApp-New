let condition = true;

function forward() {
    anime({
        targets: '.menu-small',
        translateX: ['-100%', '0'],
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });

    condition = false
}

function backward() {
    anime({
        targets: '.menu-small',
        translateX: ['0', '-100%'],
        easing: 'easeInOutQuad',
        direction: 'alternate',
        duration: 500,
        loop: false
    });

    condition = true
}

$('.menu_small_icon').click(function () {
    if (condition) {
        forward();
    } else {
        backward();
    }
});

let start = 0;
let end = 0;
$('.container').on('touchstart', function (event) {
    start = event.originalEvent.touches[0].pageX;
});
$('.container').on('touchend', function (event) {
    end = event.originalEvent.changedTouches[0].pageX;
    if (end - start >= 100 && condition) {
        forward();
    } else if (start - end >= 100 && !condition) {
        backward();
    }

});