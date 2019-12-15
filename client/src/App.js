import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import "./App.css";
import "./resources/style.css";
import Landing from "./components/Page/Landing";
import Header from "./components/Layout/Header";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import SignIn from "./components/Page/SignIn";
import BookingTrain from "./components/TrainBook-Page/BookingTrain";
import BookingCoach from "./components/CoachPlaceBook-Page/BookingCoach";
import SignUp from "./components/Page/SignUp";
import Home from "./components/Home-Page/Home";
import { Provider } from "react-redux";
import store from "./store";
import UserList from "./components/UserManage-Page/UserList";
import jwt_decode from "jwt-decode";
import setJwtToken from "./util/setJwtToken";
import { LOGIN_USER } from "./actions/types";
import { curentTimeMillis } from "./util/dateFunctions";
import { logoutPassenger } from "./actions/passengersActions";
import SecureRoute from "./util/SecuredRoute";

const jwtToken = localStorage.jwtToken;
if (jwtToken) {
  setJwtToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: LOGIN_USER,
    payload: decoded_jwtToken
  });

  if (decoded_jwtToken.exp < curentTimeMillis()) {
    store.dispatch(logoutPassenger());
    window.location.href = "/";
  }
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App site-wrap">
          <Header />
          {
            //Free routes
          }
          <Route exact path="/" component={Landing} />
          <Route exact path="/sign-in" component={SignIn} />
          <Route exact path="/sign-up" component={SignUp} />
          {
            //Secure routes
          }
          <Switch>
            <SecureRoute exact path="/home" component={Home} />
            <SecureRoute exact path="/booking/train" component={BookingTrain} />
            <SecureRoute exact path="/booking/coach" component={BookingCoach} />
            <SecureRoute exact path="/userlist" component={UserList} />
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
