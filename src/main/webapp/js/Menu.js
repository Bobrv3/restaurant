let inputQuantity;
let count;


function addOne(event) {
    event.preventDefault();

    let currentBtn = event.target;
    inputQuantity = currentBtn.parentElement.quantity;

    count = +inputQuantity.value;

    inputQuantity.value = ++count;
}

function reduceOne(event) {
    event.preventDefault();

    let currentBtn = event.target;
    inputQuantity = currentBtn.parentElement.quantity;

    count = +inputQuantity.value;

    if (--count < 1) {
        count = 1;
    }
    inputQuantity.value = count;
}