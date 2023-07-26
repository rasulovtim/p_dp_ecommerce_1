const url = "http://localhost:8080/api"

const form = document.getElementById('form')

const searchContent = document.getElementById('searchContent')

const productContent = document.getElementById('productContent')

async function getSearchResult(event) {
    event.preventDefault();

    if (form.name.value.length < 2) {
        searchContent.innerHTML = `
        <li style="font-weight: bold; text-align: center">
            Search input should contain at least two characters
        </li>
        `;
        productContent.classList.remove('active');
        searchContent.classList.remove('active');
        searchContent.classList.add('active');

    } else {
        let response = await fetch(url + '/search/product?name=' + form.name.value);

        if (response.status === 200 && form.name.value.length > 1) {
            let foundProductsList = await response.json();
            loadFoundProducts(foundProductsList);

        } else {
            searchContent.innerHTML = `
        <li style="font-weight: bold; text-align: center">No product with similar name was found</li>
        `;
            productContent.classList.remove('active');
            searchContent.classList.remove('active');
            searchContent.classList.add('active');
        }
    }
}

function loadFoundProducts(foundProductsList) {
    let dataHtml = '';
    for (let product of foundProductsList) {
        dataHtml +=
            `
    <li class="btn btn-link" onclick="getProductById(${product.id})">
        ${product.name} , ${product.price}£
    </li>
`
    }
    searchContent.innerHTML = dataHtml;
    productContent.classList.remove('active');
    searchContent.classList.add('active');
}

async function findProductsSubmitActivation() {
    form.addEventListener('submit', getSearchResult);
}

async function getProductById(id) {
    let page = await fetch(url + '/product/' + id);
    if (page.ok) {
        let product = await page.json();
        loadProduct(product);
    }
}

function loadProduct(product) {
    productContent.innerHTML = `
    <ul class="value" style="float: right; width: 50%; text-align: left">
        <li>${product.id}</li>
        <li>${product.name}</li>
        <li>${product.stockCount}</li>
        <li>${product.description}</li>
        <li>${product.isAdult}</li>
        <li>${product.code}</li>
        <li>${product.weight}</li>
        <li>${product.price}£</li>
    </ul>
    <ul class="field">
        <li>Id:</li>
        <li>Name:</li>
        <li>Stock count:</li>
        <li>Description:</li>
        <li>Is adult:</li>
        <li>Code:</li>
        <li>Weight:</li>
        <li>Price:</li>   
    </ul>   
`;
    searchContent.classList.remove('active');
    productContent.classList.add('active');
}