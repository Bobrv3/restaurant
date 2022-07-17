let categoryNameH;

function showEditCategory(categoryID, event) {
    // close previous one
    Array.prototype.slice.call(document.querySelectorAll("form.CategoryName")).forEach(el => {
        el.nextSibling.hidden = false;
        el.remove()
    })

    // create new one
    categoryNameH = event.target.parentElement
    categoryNameH.hidden = true

    const hiddenInp = document.createElement('input');
    hiddenInp.type = "hidden";
    hiddenInp.id = "hiddenInp";
    hiddenInp.name = "command";
    hiddenInp.value = "edit_category";

    const textInp = document.createElement('input');
    textInp.type = "text";
    textInp.name = "categoryName";
    textInp.id = "txtInput";
    textInp.value = categoryNameH.firstChild.nextSibling.textContent
    textInp.required = true;

    const submitInp = document.createElement('input');
    submitInp.type = "image";
    submitInp.src = "../../images/save.png"
    submitInp.id = "imgInEditCategory";
    submitInp.addEventListener("click", () => editCategory(categoryID));

    const editCategoryForm = document.createElement('form');
    let id = "editCategoryForm" + categoryID
    editCategoryForm.id = id;
    editCategoryForm.className = "CategoryName";

    const main = document.querySelector('#main-block');
    main.insertBefore(editCategoryForm, categoryNameH)

    document.querySelector(`#${id}`).appendChild(hiddenInp);
    document.querySelector(`#${id}`).appendChild(textInp);
    document.querySelector(`#${id}`).appendChild(submitInp);
}

function editCategory(categoryID) {
    event.preventDefault();

    const textInp = document.querySelector('#txtInput')
    const hiddenInp = document.querySelector('#hiddenInp')

    let url = 'http://localhost:8888/ajaxController'
    let body = `command=${hiddenInp.value}&categoryName=${textInp.value}&editedCategoryId=${categoryID}`

    const promise = sendRequest(url, 'POST', body)
    promise.then((response) => onDataReceived(response, categoryID))
        .catch((response) => {
            var error = response.statusText
            window.location = `http://localhost:8888/errorPage?errorMsg=${error}`
        })
}

function onDataReceived(response, categoryID) {
    let errorMsgElement = document.querySelector('#errorMsg');
    let editCategoryForm = '#editCategoryForm' + categoryID

    if (response.validationError) {
        if (document.querySelector(editCategoryForm).lastChild == errorMsgElement) {
            document.querySelector(editCategoryForm).removeChild(errorMsgElement);
        }
        errorMsgElement = document.createElement('h3');
        errorMsgElement.id = "errorMsg";
        errorMsgElement.innerHTML = response.message;

        document.querySelector(editCategoryForm).appendChild(errorMsgElement);
    } else {
        if (document.querySelector(editCategoryForm).lastChild == errorMsgElement) {
            document.querySelector(editCategoryForm).removeChild(errorMsgElement);
        }

        let categoryNameLbl = categoryNameH.firstChild.nextSibling
        categoryNameLbl.textContent = response.newCategoryName
        categoryNameH.hidden = false;
        document.querySelector(editCategoryForm).remove();
    }
}