console.log("this is script file");

const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};

const search = () => {
  //console.log("Searching..");
  let query = $("#search-input").val();
  console.log(query);
  if (query == "") {
    $(".search-result").hide();
  } else {
    console.log(query);

    //sending request to server
    let url = `http://localhost:8082/search/${query}`;

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        //console.log(data);

        let text = `<div class='list-group'>`;
        data.array.forEach((contact) => {
          text += `<a href='/user/${contact.cId}/contact' class='list-group-item list-group-item-action'>${contact.name}</a>`;
        });
        text += `</div`;

        $(".search-result").html(text);
        $(".search-result").show();
      });

    $(".search-result").show();
  }
};
//first request to create order

const paymentStart = () => {
  console.log("Payment Started");

  let amount = $("#payment_field").val();
  if (amount == "" || amount == null) {
    //alert("Amount is required !!");
    swal("Failed !!", "Amount is required !!", "error");
    return;
  }
  //we will use ajax to send request to server to create order
  $.ajax({
    url: "/user/create_order",
    data: JSON.stringify({ amount: amount, info: "order_request" }),
    contentType: "application/json",
    type: "POST",
    dataType: "json",
    success: function (response) {
      //invoked when success
      console.log(response);
      if (response.status == "created") {
        //open payment form
        let options = {
          key: "rzp_test_s1pHgaw6f5DjxP",
          amount: response.amount,
          currency: "INR",
          name: "Smart Contact Manager",
          description: "Donation",
          image:
            "https://learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Flcwd_logo.45da3818.png&w=640&q=75",
          order_id: response.id,
          handler: function (response) {
            console.log(response.razorpay_payment_id);
            console.log(response.razorpay_order_id);
            console.log(response.razorpay.signature);
            console.log("Payment Successful");
            //alert("Congrats ! Payment Success");
            swal("Good job!", "Congrats !! Payment Successful !", "success");
          },
          prefil: {
            name: "",
            email: "",
            contact: "",
          },
          notes: {
            address: "LearnCodeWith Durgesh",
          },
          theme: {
            color: "#3399cc",
          },
        };

        let rzp = new Razorpay(options);
        rzp.on("Payment Failed", function (response) {
          console.log(response.error.code);
          console.log(response.error.description);
          console.log(response.error.source);
          console.log(response.error.step);
          console.log(response.error.reason);
          console.log(response.error.metadata.order_id);
          console.log(response.error.metadata.payment_id);
          //alert("Oops Payment Failed");
          swal("Failed !", "Oops Payment Failed !!", "error");
        });
        rzp.open();
      }
    },
    error: function (error) {
      //invoke when error
      console.log(error);
      alert("Something Went Wrong!!");
    },
  });
};

element;
