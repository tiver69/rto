import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import classnames from "classnames";

class DisplayUser extends Component {
  constructor() {
    super();
    this.state = {
      firstName: "",
      lastName: "",
      login: "",
      editMode: false
    };
    this.toEditMode = this.toEditMode.bind(this);
  }

  toEditMode() {
    this.setState({ editMode: !this.state.editMode });
  }

  componentDidMount() {
    this.setState({
      firstName: this.props.passenger.firstName,
      lastName: this.props.passenger.lastName,
      login: this.props.passenger.login
    });
  }

  render() {
    const { passenger } = this.props;

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

          <form onSubmit="">
            <button className="btn bookmark update user">
              <span>
                <IcoMoon className="icon" icon="loop2" /> Update
              </span>
            </button>
            <h3>
              <input
                type="text"
                className="user-list update form h3"
                value={this.state.firstName}
                required="required"
              />
              <input
                type="text"
                className="user-list update form h3"
                value={this.state.lastName}
                required="required"
              />
            </h3>
            <p>
              <span className="icon-user" />
              <input
                type="text"
                className="user-list update form"
                value={this.state.login}
                required="required"
              />
            </p>
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
          <button className="bookmark user remove a-button">
            <span>
              <IcoMoon className="icon" icon="blocked" />
              Remove
            </span>
          </button>

          <h3>
            {passenger.lastName} {passenger.firstName}
          </h3>

          <p>
            <IcoMoon className="icon" icon="user" /> {passenger.login}
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

export default DisplayUser;
