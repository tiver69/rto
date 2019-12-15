import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { registerPassenger } from "../../actions/passengersActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";

class SignUp extends Component {
  constructor() {
    super();
    this.state = {
      firstName: "",
      lastName: "",
      login: "",
      password: "",
      confirmPassword: "",
      mappedErrors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSignUpSubmit = this.onSignUpSubmit.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  componentDidMount() {
    if (this.props.security.validToken) {
      this.props.history.push("/home");
    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.error) {
      this.setState({ mappedErrors: nextProps.error.mappedErrors });
    }
  }

  onSignUpSubmit(e) {
    e.preventDefault();

    const newPassenger = {
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      login: this.state.login,
      password: this.state.password,
      confirmPassword: this.state.confirmPassword
    };
    this.props.registerPassenger(newPassenger, this.props.history);
  }

  render() {
    const { mappedErrors } = this.state;

    return (
      <div
        className="site-blocks-cover overlay background-pic"
        data-stellar-background-ratio="0.5"
      >
        <div className="container">
          <div className="row align-items-center justify-content-center text-center">
            <div className="col-md-5">
              <div className="form-search-wrap p-2">
                <form onSubmit={this.onSignUpSubmit}>
                  <div className="row align-items-center">
                    <div className="w-50 p-3 no-sm-border border-right">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="info" />
                        <input
                          type="text"
                          value={this.state.firstName}
                          name="firstName"
                          className={classnames("form-control", {
                            "is-invalid": mappedErrors.firstName
                          })}
                          placeholder="First Name"
                          required="required"
                          onChange={this.onChange}
                        />
                        {mappedErrors.firstName && (
                          <span className="invalid-feedback">
                            {mappedErrors.firstName}
                          </span>
                        )}
                      </div>
                    </div>

                    <div className="w-50 p-3 no-sm-border">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="info" />
                        <input
                          type="text"
                          value={this.state.lastName}
                          name="lastName"
                          className={classnames("form-control", {
                            "is-invalid": mappedErrors.lastName
                          })}
                          placeholder="Last Name"
                          required="required"
                          onChange={this.onChange}
                        />
                      </div>
                      {mappedErrors.lastName && (
                        <span className="invalid-feedback">
                          {mappedErrors.lastName}
                        </span>
                      )}
                    </div>

                    <div className="w-50 p-3 no-sm-border border-right">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="user" />
                        <input
                          type="text"
                          value={this.state.login}
                          name="login"
                          className={classnames("form-control", {
                            "is-invalid": mappedErrors.login
                          })}
                          placeholder="Login"
                          required="required"
                          onChange={this.onChange}
                        />
                      </div>
                      {mappedErrors.login && (
                        <span className="invalid-feedback">
                          {mappedErrors.login}
                        </span>
                      )}
                    </div>

                    <div className="w-50 p-3 no-sm-border">
                      <div className="wrap-icon">
                        <IcoMoon className="icon" icon="lock" />
                        <input
                          type="password"
                          value={this.state.password}
                          name="password"
                          required="required"
                          className={classnames("form-control", {
                            "is-invalid": mappedErrors.login
                          })}
                          placeholder="Password"
                          onChange={this.onChange}
                        />
                        <input
                          type="password"
                          value={this.state.confirmPassword}
                          name="confirmPassword"
                          required="required"
                          className={classnames("form-control", {
                            "is-invalid": mappedErrors.login
                          })}
                          placeholder="Confirm password"
                          onChange={this.onChange}
                        />
                      </div>
                      {mappedErrors.password && (
                        <span className="invalid-feedback">
                          {mappedErrors.password}
                        </span>
                      )}
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

SignUp.propTypes = {
  registerPassenger: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired,
  error: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security,
  error: state.error
});

export default connect(mapStateToProps, { registerPassenger })(SignUp);
