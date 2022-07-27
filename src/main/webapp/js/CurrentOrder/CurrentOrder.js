let inputQuantity;
let count;


function addOne(event) {
    event.preventDefault();

    let currentBtn = event.target;
    inputQuantity = currentBtn.previousElementSibling;

    count = +inputQuantity.value;

    inputQuantity.value = ++count;
}

function reduceOne(event) {
    event.preventDefault();

    let currentBtn = event.target;
    inputQuantity = currentBtn.nextElementSibling;

    count = +inputQuantity.value;

    if (--count < 1) {
        count = 1;
    }
    inputQuantity.value = count;
}