import React, { Component } from "react";
import { Link } from "react-router-dom";

class Landing extends Component {
  render() {
    return (
      <div
        className="site-blocks-cover overlay background-pic"
        data-aos="fade"
        data-stellar-background-ratio="0.5"
      >
        <div className="container">
          <div className="row align-items-center justify-content-center text-center">
            <div className="col index-home-message">
              <div className="row justify-content-center mb-4">
                <div className="col-md-8 text-center">
                  <h1 data-aos="fade-up">Welcome!</h1>
                  <p>Please, login to continue</p>
                </div>
              </div>

              <Link
                to="/sign-in"
                className="btn btn-primary-o text-white"
                type="button"
              >
                Sign In
              </Link>
              <Link
                to="/sign-up"
                className="btn btn-primary-o text-white"
                type="button"
              >
                Sign Up
              </Link>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Landing;
