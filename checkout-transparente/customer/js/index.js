const mp = new MercadoPago("TEST-c1619655-02cc-4c2c-8375-7712e410406d");

  
const securityCodeElement = mp.fields.create('securityCode', {
  placeholder: "CVV"
}).mount('form-checkout__securityCode-container');

customerCards = [];

const getdata = async () => {
  const endpoint = "http://localhost:8080/customer/cards/1346741313-AwDbnu5KCEvUMV",
    response = await fetch(endpoint)
    data = await response.json()

    cards = data.results;

    cards.forEach(card => {
      appendCardToSelect(card)
    });

    console.log(data)

}
getdata();

function appendCardToSelect(card) {

  let {id, lastFourDigits, paymentMethod, securityCode} = card
  customerCards.push({id, lastFourDigits,paymentMethod,securityCode})

  const selectElement = document.getElementById('form-checkout__cardId');
  const tmpFragment = document.createDocumentFragment();
  customerCards.forEach(({ id, lastFourDigits, paymentMethod }) => {
    const optionElement = document.createElement('option');
    optionElement.setAttribute('value', id)
    optionElement.textContent = `${paymentMethod.name} ended in ${lastFourDigits}`
    tmpFragment.appendChild(optionElement);
  })
  selectElement.appendChild(tmpFragment)
}

function createToken() {

  const formElement = document.getElementById('form-checkout');
  formElement.addEventListener('submit', e => createCardToken(e));
  const createCardToken = async (event) => {
    try {
      const tokenElement = document.getElementById('token');
      if (!tokenElement.value) {
        event.preventDefault();
        const tokenCard = await mp.fields.createCardToken({
          cardId: document.getElementById('form-checkout__cardId').value
        });
        tokenElement.value = tokenCard.id;
        console.log("Token criado", tokenCard);
      }

      const orderData = {
        installments: 1,
        payer: {
          identification : {
            id: "1346741313-AwDbnu5KCEvUMV"
          }
        },
        transaction_amount: parseFloat(document.getElementById("form-checkout-amount").value),
        token: tokenElement.value,
      };

      console.log("Order data => ",orderData)
      fetch("http://localhost:8080/payment/savedcard", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(orderData),
      })
        .then(function (response) {
          return response.json()
        })
        .then(function (data) {
          alert("Pagamento aprovado com sucesso")
          window.location.reload()
        })
        .catch(function () {
          alert("Unexpected error");
          $('#checkout-btn').attr("disabled", false);
        });

    } catch (e) {
      console.error('error creating card token: ', e)
    }
  }
}

createToken()




