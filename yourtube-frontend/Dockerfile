# Stage 1
FROM node:16.13.2 as build-stage

WORKDIR /application
COPY package.json .
RUN npm install
COPY . .

EXPOSE 3000

CMD ["npm", "start"]