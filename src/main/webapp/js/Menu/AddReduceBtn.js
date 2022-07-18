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

function validate(evt) {
    var theEvent = evt || window.event;
    var key = theEvent.keyCode || theEvent.which;
    key = String.fromCharCode(key);
    var regex = /^[\d]+$/

    if (!regex.test(key)) {
        theEvent.returnValue = false;
        if (theEvent.preventDefault) theEvent.preventDefault();
    }
}
