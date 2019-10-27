import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import classnames from "classnames";
import {
  updatePassenger,
  removePassenger
} from "../../actions/passengersActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class DisplayUser extends Component {
  constructor() {
    super();

    // TO_DO: set property to view mode from props when errors occupied
    this.state = {
      id: "",
      firstName: "",
      lastName: "",
      login: "",
      editMode: false,
      mappedErrors: {}
    };
    this.toEditMode = this.toEditMode.bind(this);
    this.onChange = this.onChange.bind(this);
    this.onRemoveClick = this.onRemoveClick.bind(this);
    this.onUpdateSubmit = this.onUpdateSubmit.bind(this);
  }

  toEditMode() {
    this.setState({ editMode: !this.state.editMode });
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onUpdateSubmit(e) {
    e.preventDefault();

    const updatePassenger = {
      id: this.state.id,
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      login: this.state.login
    };
    this.props.updatePassenger(updatePassenger).then(status => {
      this.setState({ editMode: !status });
    });
  }

  onRemoveClick(e) {
    e.preventDefault();

    this.props.removePassenger(this.state.id);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.error) {
      this.setState({ mappedErrors: nextProps.error.mappedErrors });
    }
  }

  componentDidMount() {
    this.setState({
      id: this.props.passenger.id,
      firstName: this.props.passenger.firstName,
      lastName: this.props.passenger.lastName,
      login: this.props.passenger.login
    });
  }

  render() {
    const { passenger } = this.props;
    const { mappedErrors } = this.state;

    const editMode = () => {
      return (
        <React.Fragment>
          <button
            className="bookmark user w-150px a-button"
            onClick={this.toEditMode}
          >
            <span>
              <IcoMoon className="icon" icon="blocked" />
              Cansel
            </span>
          </button>

          {mappedErrors.passenger && (
            <div className="alert alert-danger" role="alert">
              UNEXPECTED ERROR: {mappedErrors.passenger}. Try to reload page.
            </div>
          )}

          <form onSubmit={this.onUpdateSubmit}>
            <button className="btn bookmark update user">
              <span>
                <IcoMoon className="icon" icon="loop2" /> Update
              </span>
            </button>
            <h3>
              <div className="row">
                <div className="w-50">
                  <input
                    type="text"
                    className={classnames(
                      "user-list update text-center form h3",
                      {
                        "is-invalid": mappedErrors.firstName
                      }
                    )}
                    value={this.state.firstName}
                    required="required"
                    placeholder="First name"
                    name="firstName"
                    onChange={this.onChange}
                  />
                  {mappedErrors.firstName && (
                    <span className="invalid-feedback">
                      {mappedErrors.firstName}
                    </span>
                  )}
                </div>

                <div className="w-50">
                  <input
                    type="text"
                    className={classnames(
                      "user-list update text-center form h3",
                      {
                        "is-invalid": mappedErrors.lastName
                      }
                    )}
                    value={this.state.lastName}
                    required="required"
                    placeholder="Last name"
                    name="lastName"
                    onChange={this.onChange}
                  />
                  {mappedErrors.lastName && (
                    <span className="invalid-feedback">
                      {mappedErrors.lastName}
                    </span>
                  )}
                </div>
              </div>
            </h3>

            <h4>
              <div className="row justify-content-center">
                <div className="w-50">
                  <span className="icon-user" />
                  <input
                    type="text"
                    className={classnames("user-list update text-center form", {
                      "is-invalid": mappedErrors.login
                    })}
                    value={this.state.login}
                    required="required"
                    placeholder="Login"
                    name="login"
                    onChange={this.onChange}
                  />
                  {mappedErrors.login && (
                    <span className="invalid-feedback">
                      {mappedErrors.login}
                    </span>
                  )}
                </div>
              </div>
            </h4>
          </form>
        </React.Fragment>
      );
    };

    const viewMove = () => {
      return (
        <React.Fragment>
          <button
            className="bookmark user update a-button"
            onClick={this.toEditMode}
          >
            <span>
              <IcoMoon className="icon" icon="pencil" /> Edit
            </span>
          </button>

          <button className="bookmark user promote a-button">
            <span>
              <IcoMoon className="icon" icon="star-full" /> Promote
            </span>
          </button>
          <button
            className="bookmark user remove a-button"
            onClick={this.onRemoveClick}
          >
            <span>
              <IcoMoon className="icon" icon="blocked" />
              Remove
            </span>
          </button>

          <h3>
            {this.state.firstName} {this.state.lastName}
          </h3>

          <p>
            <IcoMoon className="icon" icon="user" /> {this.state.login}
          </p>
        </React.Fragment>
      );
    };

    return (
      <div
        className={classnames("display-inherit", {
          "b-color-for-user-update-mode": this.state.editMode
        })}
      >
        <div className="d-block d-md-flex listing-horizontal">
          <div
            className={classnames("img d-block user-display-background-pic", {
              "user-update-background-pic": this.state.editMode
            })}
          >
            <span className="category">USER</span>
            {/* TO_DO: check if admin */}
          </div>

          <div className="lh-content">
            {!this.state.editMode && viewMove()}
            {this.state.editMode && editMode()}
            <p>
              <IcoMoon className="icon" icon="calculator" /> Total tickets -{" "}
              {passenger.totalTickets}
            </p>

            <p>
              <IcoMoon className="icon" icon="calendar" /> Last active -{" "}
              {passenger.lastActive}
            </p>
          </div>
        </div>
      </div>
    );
  }
}

DisplayUser.propTypes = {
  updatePassenger: PropTypes.func.isRequired,
  removePassenger: PropTypes.func.isRequired,
  error: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  error: state.error
});

export default connect(
  mapStateToProps,
  { updatePassenger, removePassenger }
)(DisplayUser);
