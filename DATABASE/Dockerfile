FROM arm64v8/mysql:latest

# Copy the database schema to the container
COPY ../../Docker/DATABASE/schema.sql /docker-entrypoint-initdb.d/

# Set environment variables
ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_DATABASE=auth
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password

# Expose the MySQL port
EXPOSE 3305