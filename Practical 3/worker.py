from mpi4py import MPI

OUTPUT_FILE = 'received_chunk_'
comm = MPI.COMM_WORLD
rank = comm.Get_rank()

while True:
    data = comm.recv(source=0, tag=MPI.ANY_TAG)
    if data is None:
        break

    with open(f"{OUTPUT_FILE}{rank}.txt", 'ab') as file:
        file.write(data)