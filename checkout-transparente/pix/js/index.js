const mp = new MercadoPago("TEST-c1619655-02cc-4c2c-8375-7712e410406d");

document.getElementById("checkout-btn").addEventListener("click", function () {
  
  $('#checkout-btn').attr("disabled", false);

    const orderData = {
      payerFirstName: document.getElementById("form-checkout__payerFirstName").value,
      payerLastName: document.getElementById("form-checkout__payerLastName").value,
      email: document.getElementById("form-checkout__email").value,
      identificationType: document.getElementById("form-checkout__identificationType").value,
      identificationNumber: document.getElementById("form-checkout__identificationNumber").value,
      transactionAmount: parseFloat(document.getElementById("transactionAmount").value),
      description: document.getElementById("description").value,
      paymentMethodId: document.getElementById("form-checkout__method").value,
    };
  
    console.log("Dados:" , orderData)
    fetch("http://localhost:8080/payment/offline", {
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
        console.log(data)
        if(data.paymentMethodId == "pix"){
          var a = document.getElementById('link'); 
          a.href = data.pointOfInteraction.transactionData.ticketUrl

          let img = document.getElementById('image');
          img.src= "data:image/gif;base64," + data.pointOfInteraction.transactionData.qrCodeBase64          
        } else {
          window.open(data.transactionDetails.externalResourceUrl, '_blank');
        }
      })
      .catch(function () {
        alert("Unexpected error");
        $('#checkout-btn').attr("disabled", false);
      });
  });

(async function getIdentificationTypes() {
    try {
      const identificationTypes = await mp.getIdentificationTypes();
      const identificationTypeElement = document.getElementById('form-checkout__identificationType');

      createSelectOptions(identificationTypeElement, identificationTypes);
    } catch (e) {
      return console.error('Error getting identificationTypes: ', e);
    }
  })();

  function createSelectOptions(elem, options, labelsAndKeys = { label: "name", value: "id" }) {
    const { label, value } = labelsAndKeys;

    elem.options.length = 0;

    const tempOptions = document.createDocumentFragment();

    options.forEach(option => {
      const optValue = option[value];
      const optLabel = option[label];

      const opt = document.createElement('option');
      opt.value = optValue;
      opt.textContent = optLabel;

      tempOptions.appendChild(opt);
    });

    elem.appendChild(tempOptions);
  }
