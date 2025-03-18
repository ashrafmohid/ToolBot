Toolbot - Intelligent SSH Command Executor

OVERVIEW
Toolbot is a powerful automation tool that extracts Linux commands from a LLM, allowing users to select commands and
executes them via SSH remote. It ensures security by storing SSH credentials in an encrypted format that only the
program can decrypt.

FEATURES
AI-Generated Commands: Utilizes LangChain4j and an AI model to generate Linux commands.
User Selection: Provides users with multiple command options to choose from.
Secure SSH Execution: Executes the selected command on a remote machine via SSH.
Encrypted Credentials Storage: Uses encryption to securely store SSH credentials, ensuring secure authentication.

Works with OLLAMA, currently can only work with a locally run model. Can work without SSH using commands on a local
machine.
SSH functionality not implemented yet.

FUTURE GOALS
Rationalize terminal outputs.
Run decentralized model.
Greater context using RAG and vector database.
Rollback commands.
Create bash scripts.
Fine tune a model specifically for this application.
Security - Need to add encrypting SSH private key file
           Need to check if a private key exists
           Add password protected private keys (id_rsa)

// Copyright (C) 2025 shroof
// All rights reserved