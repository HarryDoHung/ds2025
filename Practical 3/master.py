from mpi4py import MPI
import os

CHUNK_SIZE = 2048 
FILE_TO_SEND = 'testing_file.txt'

comm = MPI.COMM_WORLD
size = comm.Get_size()
rank = comm.Get_rank()

if rank == 0:
    with open(FILE_TO_SEND, 'rb') as file:
        chunk_id = 0
        while (data := file.read(CHUNK_SIZE)):
            dest = (chunk_id % (size - 1)) + 1
            comm.send(data, dest=dest, tag=chunk_id)
            chunk_id += 1

    for i in range(1, size):
        comm.send(None, dest=i, tag=0)  