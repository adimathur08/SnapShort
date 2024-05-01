# Set error action preference to stop the script if any command fails
$ErrorActionPreference = "Stop"

# Build Database-Access Docker image
Set-Location "D:\Personal Codebase\SnapShort\database-access"
docker build -t database-access:1.0 .

# Check if the build was successful
if ($?) {
    Write-Host "Database-Access Docker image built successfully."
} else {
    Write-Error "Error: Database-Access Docker image build failed."
    exit 1
}

# Build User-Management Docker image
Set-Location "D:\Personal Codebase\SnapShort\user-management"
docker build -t user-management:1.0 .

# Check if the build was successful
if ($?) {
    Write-Host "user-management Docker image built successfully."
} else {
    Write-Error "Error: user-management Docker image build failed."
    exit 1
}

# Build Database-Access Docker image
Set-Location "D:\Personal Codebase\SnapShort\post-content"
docker build -t post-content:1.0 .

# Check if the build was successful
if ($?) {
    Write-Host "post-content Docker image built successfully."
} else {
    Write-Error "Error: post-content Docker image build failed."
    exit 1
}



# Create docker network
Write-Host "Trying to create a network named snapshort"
if (-not (docker network inspect snapshort)) {
    docker network create snapshort
}


# Run Docker containers
docker run --network snapshort -d -p 8080:8080 --name database-access-service database-access:1.0
# Check if the container is running
if ($?) {
    Write-Host "database-access Docker container started successfully."
} else {
    Write-Error "Error: Failed to start database-access Docker container."
    exit 1
}

docker run --network snapshort -d -p 8081:8081 --name user-management-service user-management:1.0
if ($?) {
    Write-Host "user-management Docker container started successfully."
} else {
    Write-Error "Error: Failed to start user-management Docker container."
    exit 1
}

docker run --network snapshort -d -p 8082:8082 --name post-content-service post-content:1.0
if ($?) {
    Write-Host "post-content Docker container started successfully."
} else {
    Write-Error "Error: Failed to start post-content Docker container."
    exit 1
}