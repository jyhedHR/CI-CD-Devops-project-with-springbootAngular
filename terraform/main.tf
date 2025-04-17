terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 3.0"
    }
  }
}

provider "azurerm" {
  features {}
}

resource "azurerm_resource_group" "rg" {
  name     = "MyResourceGroup"
  location = "francecentral"
}

resource "azurerm_virtual_network" "vnet" {
  name                = "MyVnet"
  address_space       = ["10.0.0.0/16"]
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
}

resource "azurerm_subnet" "subnet" {
  name                 = "MySubnet"
  resource_group_name  = azurerm_resource_group.rg.name
  virtual_network_name = azurerm_virtual_network.vnet.name
  address_prefixes     = ["10.0.2.0/24"]
}

# Public IP for test VM
resource "azurerm_public_ip" "test_vm_ip" {
  name                = "test-vm-ip"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  allocation_method   = "Static"
  sku                 = "Basic"
}

# Public IP for prod VM
resource "azurerm_public_ip" "prod_vm_ip" {
  name                = "prod-vm-ip"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  allocation_method   = "Static"
  sku                 = "Basic"
}

resource "azurerm_network_interface" "test_nic" {
  name                = "test-nic"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = azurerm_subnet.subnet.id
    private_ip_address_allocation = "Dynamic"
    public_ip_address_id          = azurerm_public_ip.test_vm_ip.id
  }
}

resource "azurerm_network_interface" "prod_nic" {
  name                = "prod-nic"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = azurerm_subnet.subnet.id
    private_ip_address_allocation = "Dynamic"
    public_ip_address_id          = azurerm_public_ip.prod_vm_ip.id
  }
}

resource "azurerm_linux_virtual_machine" "test_vm" {
  name                = "test-vm"
  resource_group_name = azurerm_resource_group.rg.name
  location            = azurerm_resource_group.rg.location
  size                = "Standard_B2ms"
  admin_username      = "touta"
  admin_password      = "Touta*123"
  network_interface_ids = [
    azurerm_network_interface.test_nic.id,
  ]
  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }
source_image_reference {
  publisher = "Canonical"
  offer     = "UbuntuServer"
  sku       = "19_04-gen2"
  version   = "19.04.202001220"
}
  admin_ssh_key {
    username   = "touta"
    public_key = file("terraform_vm_key.pub")
  }
}

resource "azurerm_linux_virtual_machine" "prod_vm" {
  name                = "prod-vm"
  resource_group_name = azurerm_resource_group.rg.name
  location            = azurerm_resource_group.rg.location
  size                = "Standard_B2ms"
  admin_username      = "touta"
  admin_password      = "Touta*123"
  network_interface_ids = [
    azurerm_network_interface.prod_nic.id,
  ]
  os_disk {
    caching              = "ReadWrite"
    storage_account_type = "Standard_LRS"
  }
source_image_reference {
  publisher = "Canonical"
  offer     = "UbuntuServer"
  sku       = "19_04-gen2"
  version   = "19.04.202001220"
}
  admin_ssh_key {
    username   = "touta"
    public_key = file("terraform_vm_key.pub")
  }
}

output "test_vm_private_ip" {
  value = azurerm_network_interface.test_nic.private_ip_address
}

output "prod_vm_private_ip" {
  value = azurerm_network_interface.prod_nic.private_ip_address
}

output "test_vm_public_ip" {
  value = azurerm_public_ip.test_vm_ip.ip_address
}

output "prod_vm_public_ip" {
  value = azurerm_public_ip.prod_vm_ip.ip_address
}