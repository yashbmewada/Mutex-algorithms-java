#!/bin/bash
#SBATCH -J ybmBakeryLock
#SBATCH -o ybmBakeryLock.o%j
#SBATCH -n 48
#SBATCH -p development
#SBATCH -N 1
#SBATCH -t 1:30:00
#SBATCH --mail-user=ybm170030@utdallas.edu
#SBATCH --mail-type=begin
#SBATCH --mail-type=end

java -cp bin Bakery.Main
