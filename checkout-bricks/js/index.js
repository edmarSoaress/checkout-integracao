// Add SDK credentials
// REPLACE WITH YOUR PUBLIC KEY AVAILABLE IN: https://developers.mercadopago.com/panel
const mp = new MercadoPago('TEST-c1619655-02cc-4c2c-8375-7712e410406d');

const bricksBuilder = mp.bricks();

const renderPaymentBrick = async (bricksBuilder) => {
  const settings = {
    initialization: {
      /*
       "amount" é o valor total a ser pago por todos os meios de pagamento
     com exceção da Conta Mercado Pago e Parcelamento sem cartão de crédito, que tem seu valor de processamento determinado no backend através do "preferenceId"
      */
      amount: 10,
      preferenceId: "<PREFERENCE_ID>",
    },
    customization: {
      paymentMethods: {
        ticket: "all",
        bankTransfer: "all",
        creditCard: "all",
        debitCard: "all",
        mercadoPago: "all",
      },
    },
    callbacks: {
      onReady: () => {
        /*
         Callback chamado quando o Brick estiver pronto.
         Aqui você pode ocultar loadings do seu site, por exemplo.
        */
      },
      onSubmit: ({ selectedPaymentMethod, formData }) => {
        console.log(formData)
        // callback chamado ao clicar no botão de submissão dos dados
        return new Promise((resolve, reject) => {
          fetch("http://localhost:8080/payment", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(formData),
          })
            .then((response) => response.json())
            .then((response) => {
              // receber o resultado do pagamento
              alert("Pagamento processado com sucesso")
              window.location.reload()
              
              resolve();
            })
            .catch((error) => {
              // lidar com a resposta de erro ao tentar criar o pagamento
              reject();
            });
        });
      },
      onError: (error) => {
        // callback chamado para todos os casos de erro do Brick
        console.error(error);
      },
    },
  };
  window.paymentBrickController = await bricksBuilder.create(
    "payment",
    "paymentBrick_container",
    settings
  );
 };
 renderPaymentBrick(bricksBuilder);
