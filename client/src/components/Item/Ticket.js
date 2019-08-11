import React, { Component } from "react";
import IcoMoon from "react-icomoon";
import { Link } from "react-router-dom";

class Ticket extends Component {
  render() {
    const { ticket } = this.props;
    return (
      <div className="d-block d-md-flex listing-horizontal">
        <div className="img d-block ticket-active-background-pic">
          <span className="category">
            COACH - {ticket.trainCoach.number}
            <br />
            PLACE - {ticket.place}
            <br />
            PRICE - {ticket.price}₴
            <br />
          </span>
        </div>
        {/* <div className="img d-block ticket-history-background-pic">
             <span className="category">
                   <fmt:message key="ticket.coach"/> - <c:out value="${ticket.getTicket().getTrainCoach().getNumber()}"/><br/>
                   <fmt:message key="ticket.place"/> - <c:out value="${ticket.getTicket().getPlace()}"/><br/>
                   <fmt:message key="ticket.price"/> - <c:out value="${ticket.getTicket().getPrice()}"/>₴<br/>
             </span>
           </div> */}

        <div className="lh-content">
          <Link
            to="/todo"
            href="returnTicket?ticketId=${ticket.getTicket().getId()}"
            className="bookmark"
            method="delete"
          >
            <span>
              <IcoMoon className="icon" icon="blocked" />
              Return
            </span>
          </Link>

          <h3>
            {" "}
            {ticket.departureStation.name} - {ticket.destinationStation.name}
          </h3>

          <p>
            <IcoMoon className="icon" icon="road" /> Train number #
            {ticket.trainCoach.train.id}
          </p>

          <p>
            <IcoMoon className="icon" icon="clock" /> Departure -{" "}
            {ticket.departureDate} TIME_TO_DO
          </p>

          <p>
            <IcoMoon className="icon" icon="clock" /> Arrival - DATE_TO_DO
            TIME_TO_DO
          </p>
        </div>
      </div>
    );
  }
}

export default Ticket;
