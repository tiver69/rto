import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import "./App.css";
import "./resources/style.css";
import Landing from "./components/Layout/Landing";
import Header from "./components/Layout/Header";
import { BrowserRouter as Router, Route } from "react-router-dom";
import SignIn from "./components/Page/SignIn";
import SignUp from "./components/Page/SignUp";
import Home from "./components/Page/Home";
import { Provider } from "react-redux";
import store from "./store";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App site-wrap">
          <Header />
          <Route exact path="/" component={Landing} />
          <Route exact path="/home/:passengerId" component={Home} />
          <Route exact path="/sign-in" component={SignIn} />
          <Route exact path="/sign-up" component={SignUp} />
        </div>
      </Router>
    </Provider>
  );
}

export default App;
