export const currentDate = () => {
  var now = new Date();
  var month = new Date().getMonth() + 1;
  if (month < 10) month = "0" + month;
  var day = new Date().getDate();
  if (day < 10) day = "0" + day;

  return now.getFullYear() + "-" + month + "-" + day;
};

export const curentTimeMillis = () => {
  return Date.now() / 1000;
};
