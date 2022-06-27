let inputQuantity;
let count;


function addOne(event) {
    let currentBtn = event.target;
    inputQuantity = currentBtn.parentElement.quantity;

    count = +inputQuantity.value;

    inputQuantity.value = ++count;


}

function reduceOne(event) {
    let currentBtn = event.target;
    inputQuantity = currentBtn.parentElement.quantity;

    count = +inputQuantity.value;

    if (--count < 1) {
        count = 1;
    }
    inputQuantity.value = count;
}