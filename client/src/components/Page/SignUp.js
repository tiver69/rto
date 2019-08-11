import React, { Component } from "react";
import IcoMoon from "react-icomoon";

class SignUp extends Component {
  render() {
    return (
      <div
        className="site-blocks-cover overlay background-pic"
        data-stellar-background-ratio="0.5"
      >
        <div className="container">
          <div className="row align-items-center justify-content-center text-center">
            <div className="col-md-5">
              <div className="form-search-wrap p-2">
                <form action="signup" method="post">
                  <div className="row align-items-center">
                    <div className="w-50 p-3 no-sm-border border-right">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="info" />
                        <input
                          type="text"
                          className="form-control"
                          placeholder="First Name"
                          name="firstName"
                          required="required"
                        />
                      </div>
                    </div>

                    <div className="w-50 p-3 no-sm-border">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="info" />
                        <input
                          type="text"
                          className="form-control"
                          placeholder="Last Name"
                          name="lastName"
                          required="required"
                        />
                      </div>
                    </div>

                    <div className="w-50 p-3 no-sm-border border-right">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="user" />
                        <input
                          placeholder="Login"
                          type="text"
                          className="form-control"
                          name="login"
                          required="required"
                        />
                      </div>
                    </div>

                    <div className="w-50 p-3 no-sm-border">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="lock" />
                        <input
                          type="password"
                          name="password"
                          required="required"
                          className="form-control"
                          placeholder="Password"
                        />
                      </div>
                    </div>

                    <div className="w-100 p-3 ml-auto text-center">
                      <input
                        type="submit"
                        className="btn btn-primary-o"
                        value="Sign Up"
                      />
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default SignUp;
