const tbody = document.querySelector('#tbody');

const getdata = async () => {
  const endpoint = "http://localhost:8080/payment/options",
        response = await fetch(endpoint),
        data = await response.json(),
        res = data.results;

 res.forEach(result => {
    let {name, status, secureThumbnail} = result;
    tbody.innerHTML += `<tr>
        <td>${name}</td>
        <td>${status}</td>
        <td><img src=${secureThumbnail} ></img></td>
    </tr>`;
 });

}
getdata();