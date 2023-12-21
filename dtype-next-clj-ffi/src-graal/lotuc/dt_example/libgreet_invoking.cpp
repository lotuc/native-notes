#include <iostream>
#include <libgreet.h>

int main(int argc, char* argv[]) {
  graal_isolate_t *isolate = NULL;
  graal_isolatethread_t *thread = NULL;

  if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
    fprintf(stderr, "initialization error\n");
    return 1;
  }

  long r = greet(thread, (void*) "world");
  std::cout << "name size: " << r << std::endl;

  return 0;
}
