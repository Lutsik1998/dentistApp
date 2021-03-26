FROM node:14.3.0-alpine3.10 as build-step
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .
EXPOSE 4200
CMD ng serve