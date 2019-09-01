import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { Link } from "react-router-dom";
import classnames from "classnames";

class Ticket extends Component {
  render() {
    const { ticket } = this.props;
    return (
      <div className="d-block d-md-flex listing-horizontal">
        <div
          className={classnames(
            "img d-block",
            {
              "ticket-active-background-pic": this.props.active
            },
            { "ticket-history-background-pic": !this.props.active }
          )}
        >
          <span className="category">
            COACH - {ticket.coachNumber}
            <br />
            PLACE - {ticket.place}
            <br />
            PRICE - {ticket.price}₴
            <br />
          </span>
        </div>

        <div className="lh-content">
          {ticket.active && (
            <Link to="/todo" className="bookmark">
              <span>
                <IcoMoon className="icon" icon="blocked" />
                Return
              </span>
            </Link>
          )}
          <h3>
            {" "}
            {ticket.departureStation} - {ticket.destinationStation}
          </h3>

          <p>
            <IcoMoon className="icon" icon="road" /> Train number #
            {ticket.trainId}
          </p>

          <p>
            <IcoMoon className="icon" icon="clock" /> Departure -{" "}
            {ticket.departureDateTime}
          </p>

          <p>
            <IcoMoon className="icon" icon="clock" /> Arrival -{" "}
            {ticket.arrivalDateTime}
          </p>
        </div>
      </div>
    );
  }
}

export default Ticket;
