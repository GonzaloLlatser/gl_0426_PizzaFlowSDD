# 🍕 PizzaFlow — Order Management Spec

---

## 01 — METADATA

Spec-ID: PF-ORDER-001  
Product: PizzaFlow  
Capability: Order Management  
Version: v1.0  
Author: Gonzalo  
Status: Draft  
Date: 2026-04-23  

---

## 02 — FUNCTIONAL DESCRIPTION

PizzaFlow allows customers to create and manage pizza orders through a defined lifecycle.  
The system ensures consistency, validation of business rules, and controlled state transitions.  
Orders move from creation to delivery, enforcing constraints at each step.  
The goal is to guarantee reliable order processing and prevent invalid states.

---

## 03 — ACTORS & PRECONDITIONS

### Actors

- **Customer**
  - Creates orders
  - Adds items
  - Confirms and pays

- **System**
  - Validates rules
  - Manages state transitions
  - Calculates totals

- **Kitchen**
  - Prepares orders
  - Marks orders as ready

---

### Preconditions

- Customer must exist
- Product must exist
- Product must be available
- System must be operational
- Order must exist for state transitions

---

## 04 — MAIN FLOW (Happy Path)

1. Customer creates a new order
2. Customer adds products to the order
3. Customer confirms the order
4. Customer performs payment
5. System validates payment
6. Order moves to kitchen
7. Kitchen prepares the order
8. Kitchen marks the order as READY
9. Order is delivered to the customer

---

## 05 — BUSINESS RULES

BR-001: Orders must start in `CREATED` state  
BR-002: Orders cannot be confirmed if empty  
BR-003: Orders must be confirmed before payment  
BR-004: Orders must be paid before preparation  
BR-005: Only orders in preparation can be marked as READY  
BR-006: Only READY orders can be delivered  
BR-007: Delivered orders cannot be cancelled  
BR-008: Cancelled orders cannot transition further  
BR-009: Product must be available to be added  
BR-010: Quantity must be greater than zero  
BR-011: Order total must equal sum of item subtotals  

---

## 06 — STATE MACHINE

### States

CREATED  
CONFIRMED  
PAID  
IN_PREPARATION  
READY  
DELIVERED  
CANCELLED  

---

### Transitions

#### CREATED → CONFIRMED
- Trigger: confirm_order
- Guard:
  - Order must contain at least one item
- Side Effects:
  - Lock order (no further item modifications)

---

#### CONFIRMED → PAID
- Trigger: payment_success
- Guard:
  - Order must not be cancelled
- Side Effects:
  - Store payment reference
  - Log transaction

---

#### PAID → IN_PREPARATION
- Trigger: start_preparation
- Guard:
  - Payment must be completed
- Side Effects:
  - Notify kitchen

---

#### IN_PREPARATION → READY
- Trigger: mark_ready
- Guard:
  - Order must be in preparation
- Side Effects:
  - Notify customer

---

#### READY → DELIVERED
- Trigger: deliver_order
- Guard:
  - Order must be ready
- Side Effects:
  - Close order lifecycle

---

#### ANY → CANCELLED
- Trigger: cancel_order
- Guard:
  - Order must not be delivered
- Side Effects:
  - Stop processing
  - Mark order as terminated

---

## 07 — EDGE CASES

| ID | Scenario | Expected Behavior |
|----|---------|-----------------|
| EC-001 | Confirm empty order | Reject |
| EC-002 | Pay without confirmation | Reject |
| EC-003 | Start preparation without payment | Reject |
| EC-004 | Add unavailable product | Reject |
| EC-005 | Quantity ≤ 0 | Reject |
| EC-006 | Cancel delivered order | Reject |
| EC-007 | Duplicate payment request | Must be idempotent or rejected |
| EC-008 | Modify order after confirmation | Reject |
| EC-009 | Mark ready without preparation | Reject |
| EC-010 | Deliver cancelled order | Reject |

---

## 08 — BDD ACCEPTANCE

### Scenario 1 — Confirm valid order

Given an order in CREATED state with at least one item  
When the customer confirms the order  
Then the order status becomes CONFIRMED  

---

### Scenario 2 — Reject empty order confirmation

Given an order in CREATED state with no items  
When the customer confirms the order  
Then the system rejects the action  

---

### Scenario 3 — Pay confirmed order

Given an order in CONFIRMED state  
When payment is successful  
Then the order status becomes PAID  

---

### Scenario 4 — Reject invalid state transition

Given an order in CREATED state  
When trying to pay  
Then the system rejects the action  

---

### Scenario 5 — Deliver ready order

Given an order in READY state  
When delivery is executed  
Then the order status becomes DELIVERED  

---

## 09 — INTEGRATION NEEDS

- Payment System (future integration)
- Product Catalog Service
- Notification System (optional)
- Kitchen display system (optional)

---

## 10 — GEOGRAPHIC ADAPTATIONS

- Currency may vary (EUR, USD)
- Timezone handling for timestamps
- Localization of product names and messages

---

# END OF SPEC