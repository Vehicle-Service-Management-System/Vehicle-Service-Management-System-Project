# AutoServe — Vehicle Service Management System

AutoServe is a comprehensive, local-first platform designed for modern vehicle service bays and dealership service centers. Developed securely to store and process data locally using a SQLite backend with Node.js and an intuitive vanilla JavaScript frontend.

## Features
- **Clean Bay Dealership Theme**: Fully responsive corporate aesthetic prioritizing clarity and ease of use.
- **Local-First Architecture**: Powered by a robust SQLite database, ensuring speed and absolute data sovereignty—no cloud subscriptions required.
- **Role-Based Access Control (RBAC)**: Securely manage operators via JWT and Bcrypt caching. Custom views support `Owner`, `Manager`, and `Employee` access levels.
- **Dynamic Service Workflow**: Multistage job logging interface ensuring precise entry of customers, vehicles, assigned logic, and cost tracking.
- **Analytics & Exporting**: Comprehensive live dashboards tracking service loads, revenue metrics, and fleet reporting parameters dynamically.

## Tech Stack
- Frontend: HTML5, CSS3, Vanilla JavaScript (Anime.js for smooth micro-interactions).
- Backend: Node.js, Express.
- Database: SQLite (via `better-sqlite3`).
- Security: JSON Web Tokens (JWT), bcrypt.js.

## Getting Started

### Prerequisites
- [Node.js](https://nodejs.org/) (v16.x or newer recommended)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Vehicle-Service-Management-System/Vehicle-Service-Management-System-Project.git
   cd Vehicle-Service-Management-System-Project/autoserve
   ```
2. Install the necessary dependencies:
   ```bash
   npm install
   ```

### Running the Application
To run the server in development mode:
```bash
npm run dev
```

To run the application in production mode:
```bash
npm start
```

Once the server builds successfully, navigate to the web client at `http://localhost:3000`.

## Directory Structure
```text
autoserve/
├── backend/
│   ├── config/      - Setup properties
│   ├── controllers/ - Request response mappings
│   ├── middleware/  - Authentication routines
│   ├── routes/      - API structure configurations
│   └── server.js    - Entry point
├── frontend/
│   ├── css/         - Dealership theme stylesheets
│   ├── js/          - Data handling layers and API interceptors
│   │   └── views/   - Web view components (SPA routed)
│   └── index.html   - Single Page Application anchor
└── package.json     - Node dependencies
```

## Contributing
Please see existing branch conventions and feature proposal issues before directly merging to the primary branch.

## License
MIT License
