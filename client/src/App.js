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

function App() {
  return (
    <Router>
      <div className="App site-wrap">
        <Header />
        <Route exact path="/" component={Landing} />
        <Route exact path="/home" component={Home} />
        <Route exact path="/sign-in" component={SignIn} />
        <Route exact path="/sign-up" component={SignUp} />
      </div>
    </Router>
  );
}

export default App;
