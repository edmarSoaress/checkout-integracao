// Add SDK credentials
// REPLACE WITH YOUR PUBLIC KEY AVAILABLE IN: https://developers.mercadopago.com/panel
const mp = new MercadoPago('TEST-c1619655-02cc-4c2c-8375-7712e410406d');

const bricksBuilder = mp.bricks();

const renderCardPaymentBrick = async (bricksBuilder) => {
  const settings = {
    initialization: {
      amount: 100, // valor total a ser pago
    },
    callbacks: {
      onReady: () => {
        /*
          Callback chamado quando o Brick estiver pronto.
          Aqui você pode ocultar loadings do seu site, por exemplo.
        */
      },
      onSubmit: (formData) => {
        // callback chamado ao clicar no botão de submissão dos dados
        console.log(formData)
        return new Promise((resolve, reject) => {
          fetch('http://localhost:8080/payment', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
          })
            .then((response) => response.json())
            .then((response) => {
              // receber o resultado do pagamento
              alert("Pagamento aprovado com sucesso")
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
   window.cardPaymentBrickController = await bricksBuilder.create(
    'cardPayment',
    'cardPaymentBrick_container',
    settings,
   );  
 };
 renderCardPaymentBrick(bricksBuilder);