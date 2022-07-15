const addCategoryBtn = document.querySelector("#add-category-btn");
addCategoryBtn.addEventListener("click", showCategoryEditForm);

function showCategoryEditForm() {
    document.querySelector('#create-category-txtbtn').hidden = true;

    const hiddenInp = document.createElement('input');
    hiddenInp.type = "hidden";
    hiddenInp.id = "hiddenInp";
    hiddenInp.name = "command";
    hiddenInp.value = "add_category";

    const textInp = document.createElement('input');
    textInp.type = "text";
    textInp.name = "categoryName";
    textInp.id = "txtInput";
    textInp.required = true;

    const submitInp = document.createElement('input');
    submitInp.type = "image";
    submitInp.src = "../../images/save.png"
    submitInp.id = "imgInEditCategory";
    submitInp.addEventListener("click", addNewCategory);

    const editCategoryForm = document.createElement('form');
    editCategoryForm.action = "restaurant";
    editCategoryForm.method = 'post';
    editCategoryForm.id = "editCategoryForm";
    editCategoryForm.className = "CategoryName";

    const main = document.querySelector('#main-block');
    main.appendChild(editCategoryForm);

    document.querySelector('#editCategoryForm').appendChild(hiddenInp);
    document.querySelector('#editCategoryForm').appendChild(textInp);
    document.querySelector('#editCategoryForm').appendChild(submitInp);
}

function addNewCategory(event) {
    event.preventDefault();

    const textInp = this.parentElement.txtInput;
    const hiddenInp = this.parentElement.hiddenInp;

    const url = "http://localhost:8888/ajaxController"
    const body = `command=${hiddenInp.value}&categoryName=${textInp.value}`

    const promise = sendRequest(url, 'POST', body)
    promise.then(onDataReceived)
        .catch((response) => {
            var error = response.statusText
            window.location = `http://localhost:8888/errorPage?errorMsg=${error}`
        })
}

var errorMsg;

function onDataReceived(response) {
    if (response.validationError) {
        if (document.querySelector('#editCategoryForm').lastChild == errorMsg) {
            document.querySelector('#editCategoryForm').removeChild(errorMsg);
        }
        errorMsg = document.createElement('h3');
        errorMsg.id = "errorMsg";
        errorMsg.innerHTML = response.message;

        document.querySelector('#editCategoryForm').appendChild(errorMsg);
    } else {
        if (document.querySelector('#editCategoryForm').lastChild == errorMsg) {
            document.querySelector('#editCategoryForm').removeChild(errorMsg);
        }

        var categoryId = response.id;
        var categoryName = response.name;

        const createdCategory = document.createElement('h2');
        createdCategory.className = 'CategoryName';
        createdCategory.innerHTML =
            `${categoryName} 
            <a href="/home?editedCategory=${categoryId}">
                <img src="../../images/edit.png" alt="edit" class="imgInTd">
            </a>
            <form action="restaurant" method="post">
                <input type="hidden" name="command" value="remove_category">
                <input type="hidden" name="categoryId" value=${categoryId}>

                <input type="image" src="../../images/remove.png" alt="remove" class="imgInTd">
            </form>`;

        const main = document.querySelector('#main-block');
        const createCategoryForm = document.querySelector('#create-category-txtbtn');
        main.insertBefore(createdCategory, createCategoryForm);

        const table = document.createElement('table');
        table.innerHTML =
            `<tr>
                <td colspan="5">
                    <a href="/home?createDish=true&categoryForAdd=${categoryId}">
                        <img src="../../images/addContent.png" alt="add dish" id="imgAddContent">
                    </a>
                </td>
            </tr>`;
        main.insertBefore(table, createCategoryForm);

        createCategoryForm.hidden = false;
        document.querySelector('#editCategoryForm').remove();
    }
}